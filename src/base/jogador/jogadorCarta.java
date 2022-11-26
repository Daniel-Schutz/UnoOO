package base.jogador;

import acao.*;
import base.*;
import cartas.*;

import java.io.PrintStream;
import java.util.ArrayList;


public class jogadorCarta extends Jogador{
	

	public jogadorCarta(String nome) {
		super(nome);
		this.inicializarMao(); 
	}
	
	/* /**
    *
    * Compra uma carta, adicionando uma carta a maoJogador,
    * @see MaoCartas
    **/
    //@Override
    /*public void comprar(Carta carta){
        this.maoJogador.receberCarta(carta);
        LOGGER.info("Carta adicionada: {}", carta.toString());
    }*/
	
	public Cor CorEscolhida() {
		
		ArrayList<Integer> qtdCor = new ArrayList<>();
		
		qtdCor.add(0); //Amarelo pos 0
		qtdCor.add(0); //Azul pos 1
		qtdCor.add(0); //Verde pos 2
		qtdCor.add(0); //Vermelho pos 3
		
		
		for(Carta c: this.getMaoJogador().getCartas()) {
			
			if(c instanceof CartaComCor) {
				
				if(c.getCor() == Cor.AMARELO) {
					int amarelo = qtdCor.get(0);
					qtdCor.set(0, amarelo+1);
				}
				
				else if(c.getCor() == Cor.AZUL) {
					int azul = qtdCor.get(1);
					qtdCor.set(1, azul+1);
				}
				
				else if(c.getCor() == Cor.VERDE) {
					int verde = qtdCor.get(2);
					qtdCor.set(2, verde+1);
				}
				
				else if(c.getCor() == Cor.VERMELHO) {
					int vermelho = qtdCor.get(3);
					qtdCor.set(3, vermelho+1);
				}
				
				
			}
			
			
		}
		
		//Parte de decidir a cor, com base no for it de cima
		
		Cor corEscolhida = Cor.AMARELO; 
		int MaiorValor = qtdCor.get(0); //Amarelo
		
		for(int i =1; i < qtdCor.size();i++) {
			
			if(qtdCor.get(i) > MaiorValor ) {
				MaiorValor = qtdCor.get(i);
				
				if(i==1) {
					
					corEscolhida = Cor.AZUL;
					
				}
				
				else if(i==2) {
					
					corEscolhida = Cor.VERDE;
				}
				
				else if(i==3) {
					
					corEscolhida = Cor.VERMELHO;
				}
			}
			
		}
		
		return corEscolhida; 
	}
	
	//Parte de escolher a cor, agora, sem sem aleatório
	
    public Cor sorteiaCor(){
    	
    	//System.out.println("Entrou");
     
        Cor corSorteada = CorEscolhida();
        LOGGER.trace("Cor escolhida: {}", corSorteada);
        return corSorteada;
    }
    
    
    //Parte de Definir Carta da Jogada
    
    protected Carta defineCartaDaJogada()
{    
    	
        Jogador DaVez;
        Carta ultimo = Jogo.roda.getUltimaCarta();
		//System.out.print(Jogo.roda.jogadorDaVez()); //tem que fazer andar
        
        ArrayList<Jogador> CopiaJogadores = Jogo.roda.getJogadores();
        
        try {
        
           DaVez = CopiaJogadores.get(Jogo.roda.getPosicaoAtual()+1);// Pegando o próximo jogador
        
		         if(DaVez.getQuantidadeCartas() <= 3) {
		    		
		    		// Busca +4 e troca cor
		        	for(Carta c : this.getMaoJogador().getCartas())
		        	{
		        		if(!(c instanceof CartaEspecialSemCor))
		        			continue;
		        		
		        		// +4 e troca cor pode ser jogado de qualquer forma
		        		return c;
		        	}
		        	
		        	// Busca bloqueio, reverso e +2
		        	for(Carta c : this.getMaoJogador().getCartas())
		        	{
		        		if(!(c instanceof CartaEspecialComCor))
		        			continue;
		        		
		        		CartaEspecialComCor ca = (CartaEspecialComCor)c;
		        		
		        		// Verifica se é a mesma cor ou se é a mesma ação pra poder jogar
		        		if(ca.getCor() == ultimo.getCor() || (ultimo instanceof CartaEspecialComCor && ca.getAcao() == ((CartaEspecialComCor)ultimo).getAcao()))
		        		{
		        			return ca;
		        		}
		        	}
		        	
		        	//Tentando fazer com que seja escolhida a cor de maior quantidade; 
		        	
		        	Cor MelhorCorDescarte = CorEscolhida();
		        	
		        	for(Carta c : this.getMaoJogador().getCartas()) {
		        		
		        		if(!(c instanceof CartaNormal))
		        			continue;
		        		
		        		if(c.getCor() != MelhorCorDescarte) {
		        			continue;
		        			
		        		}else {
		        			CartaNormal cn = (CartaNormal)c;
		        			
		        			// Se for a mesma cor pode jogar
		            		if(cn.getCor() == ultimo.getCor())
		            			return c;
		            		
		            		// Se for o mesmo número também pode jogar
		            		if(ultimo instanceof CartaNormal && ((CartaNormal)ultimo).getNumero() == cn.getNumero())
		            			return c;
		            	  }
		        			
		        	}//Fecha melhor opção de carte
		        	
		        	
		        	// Busca cartas normais caso algo de errado no de cima 
		        	for(Carta c : this.getMaoJogador().getCartas())
		        	{
		        		
		        		
		        		if(!(c instanceof CartaNormal))
		        			continue;
		        		
		        		CartaNormal cn = (CartaNormal)c;
		        		
		        		
		        		// Se for a mesma cor pode jogar
		        		if(cn.getCor() == ultimo.getCor())
		        			return c;
		        		
		        		// Se for o mesmo número também pode jogar
		        		if(ultimo instanceof CartaNormal && ((CartaNormal)ultimo).getNumero() == cn.getNumero())
		        			return c;
		        	}
		        	
		        	
		        	return null;
		        	
		        	
		        		
		  		
		        		
		        }// Proximo J <=3
        		
        //System.out.print(DaVez); 
        
        }catch(IndexOutOfBoundsException exception) {
        	//Pensar na correção 
        }
		
    		
    	
    	//Tentando fazer com que seja escolhida a cor de maior quantidade; 
    	
    	Cor MelhorCorDescarte = CorEscolhida();
    	
    	for(Carta c : this.getMaoJogador().getCartas()) {
    		
    		if(!(c instanceof CartaNormal))
    			continue;
    		
    		if(c.getCor() != MelhorCorDescarte) {
    			continue;
    			
    		}else {
    			CartaNormal cn = (CartaNormal)c;
    			
    			// Se for a mesma cor pode jogar
        		if(cn.getCor() == ultimo.getCor())
        			return c;
        		
        		// Se for o mesmo número também pode jogar
        		if(ultimo instanceof CartaNormal && ((CartaNormal)ultimo).getNumero() == cn.getNumero())
        			return c;
        	  }
    			
    	}//Fecha melhor opção de carta
        	
        	
        	
        	// Busca cartas normais caso algo de errado 
        	for(Carta c : this.getMaoJogador().getCartas())
        	{
        		
        		
        		if(!(c instanceof CartaNormal))
        			continue;
        		
        		CartaNormal cn = (CartaNormal)c;
        		
        		
        		// Se for a mesma cor pode jogar
        		if(cn.getCor() == ultimo.getCor())
        			return c;
        		
        		// Se for o mesmo número também pode jogar
        		if(ultimo instanceof CartaNormal && ((CartaNormal)ultimo).getNumero() == cn.getNumero())
        			return c;
        	}
        	
  
    	
    	// Busca bloqueio, reverso e +2
    	for(Carta c : this.getMaoJogador().getCartas())
    	{
    		if(!(c instanceof CartaEspecialComCor))
    			continue;
    		
    		CartaEspecialComCor ca = (CartaEspecialComCor)c;
    		
    		// Verifica se é a mesma cor ou se é a mesma ação pra poder jogar
    		if(ca.getCor() == ultimo.getCor() || (ultimo instanceof CartaEspecialComCor && ca.getAcao() == ((CartaEspecialComCor)ultimo).getAcao()))
    		{
    			return ca;
    		}
    	}
    	
    	// Busca cartas normais
    	for(Carta c : this.getMaoJogador().getCartas())
    	{
    		if(!(c instanceof CartaNormal))
    			continue;
    		
    		CartaNormal cn = (CartaNormal)c;
    		
    		// Se for a mesma cor pode jogar
    		if(cn.getCor() == ultimo.getCor())
    			return c;
    		
    		// Se for o mesmo número também pode jogar
    		if(ultimo instanceof CartaNormal && ((CartaNormal)ultimo).getNumero() == cn.getNumero())
    			return c;
    	}
    	
    	// Busca +4 e troca cor
    	for(Carta c : this.getMaoJogador().getCartas())
    	{
    		if(!(c instanceof CartaEspecialSemCor))
    			continue;
    		
    		// +4 e troca cor pode ser jogado de qualquer forma
    		return c;
    	}
    	
    	// Se não conseguir jogar nenhuma tem que comprar
        return null;
    	//return this.defineCartaDaJogada();
    }
	
  
    public Jogada realizarJogada(){
        LOGGER.trace("Jogador {} realizando jogada", this.getNome());
        Carta carta = null;
        Jogada jogadaRealizada = null;
        if(Jogo.roda.temAcumulo()){
            try{
                carta = defineCartaParaAcumulo(Jogo.roda.getUltimaCarta().getAcao());
            }catch (CartaSemAcao e) {
                LOGGER.error("Erro ao tentar definir carta de acúmulo: {}", e);
            }

            if(carta == null){
                LOGGER.trace("Jogador {} comprando acúmulo", this.getNome());
                comprarCartasAcumuladas(Jogo.roda.desacumular());
                jogadaRealizada = Jogada.COMPRAR_ACUMULADO;
                LOGGER.info("Jogador {} comprou acúmulo. Ficou com {} cartas", this.getNome(), this.getQuantidadeCartas());

            }else{
                descartar(carta);
                LOGGER.info("Jogador {} descartou {} para o acúmulo", this.getNome(), carta.toString());
                jogadaRealizada = Jogada.DESCARTAR;
            }
        }else{
            carta = defineCartaDaJogada();

            if(carta != null){
                if(carta instanceof CartaComAcao){
                    try {
                        Acao acaoCarta = carta.getAcao();
                            realizarAcaoDaCarta(acaoCarta);
                    } catch (CartaSemAcao e) {
                        LOGGER.error("ERRO: Carta não possui acao!");
                    }
                }
                descartar(carta);
                jogadaRealizada = Jogada.DESCARTAR;

            }else{
                LOGGER.info("Jogador {} precisou comprar uma carta", this.getNome());
            	Jogo.roda.comprar(1, this);
                jogadaRealizada = Jogada.COMPRAR;
            }
        }
        return jogadaRealizada;
    }
}









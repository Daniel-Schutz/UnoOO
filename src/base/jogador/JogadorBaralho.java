package base.jogador;

import acao.Acao;
import base.Jogo;
import cartas.Carta;
import cartas.CartaComAcao;
import cartas.CartaSemAcao;

/**
 * Subclasse da superclasse 'Jogador' que representa o jogador criado pelo grupo baralho
 * 
 * @author Jecelen Adriane Campos e Guilherme Bispo Cupertino. Grupo Baralho.
 * 
 */
public class JogadorBaralho extends Jogador{
	/**
	 * Método construtor que recebe como parâmetro o nome do jogador que representa o grupo baralho e inicializa a mao do jogador.
	 * @param nome
	 */
	public JogadorBaralho(String nome){
		super(nome);
		super.inicializarMao();
		LOGGER.info("JogadorBaralho criado com sucesso\n");
	}
 /**
  * Retorna a jogada a ser realizada pelo JogadorBaralho com base na última carta jogada no monte de descarte ou acumulo de cartas e as cartas na mão do jogador
  * @return a jogada do JogadorBaralho.
  */
	public Jogada realizarJogada(){
        LOGGER.trace("Jogador {} realizando jogada", this.getNome());
        Jogada jogadaRealizada = null;
        Carta carta = null;
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
        LOGGER.info("Jogada do JogadorBaralho realizada\n");
        return jogadaRealizada;
		
	}
}


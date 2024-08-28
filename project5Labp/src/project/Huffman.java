package project;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Classe Huffman
 * 
 * @author Afonso Santos - fc59808
 *
 */
public class Huffman {
	
	/**
	 * Devolve um HashMap com cOdigos Huffman
	 * 
	 * @param corpus corpo da mensagem
	 * @return Mapa com os cOdigos Huffman
	 */
	public static HashMap<Character, String> getCodes(String corpus) {
		return treeFromCorpus(corpus).getHuffmanCodes();
	}
	
	/**
	 * RepresentaCAo dos cOdigos em Strings
	 * 
	 * @param codes cOdigos por representar
	 * @return cOdigos representados em String
	 */
	public static String codesToString(HashMap<Character, String> codes) {
		StringBuilder codesRepresentation = new StringBuilder();
		
		SortedMap<Character, String> orderedCodes = new TreeMap<Character, String>(codes);

		for (Object entry : orderedCodes.entrySet()) {
			codesRepresentation.append(entry);
			codesRepresentation.append(System.lineSeparator());
		}

		return codesRepresentation.toString();		
	}
	
	/**
	 * Codifica a string passada ao primeiro parAmetro 
	 * usando os cOdigos passados do segundo parAmetro 
	 * 
	 * @param message mensagem para codificar
	 * @param codes cOdigos dos caracteres
	 * @return uma string apenas de 0s e 1s
	 */
	public static String encode(String message, HashMap<Character, String> codes) {
		StringBuilder bob = new StringBuilder();
		
		//itera pelos caracteres da mensagem
		for (int i = 0; i < message.length(); i++) {
			char Key = message.charAt(i);
			
			//caso exista Key
			if (codes.containsKey(Key)){
				bob.append(codes.get(Key));
			}
			//caso não exista Key
			else {
				bob.append("-");
			}
		}
		
		return bob.toString();
	}
	
	/**
	 * Devolve uma nova HuffmanTree 
	 * 
	 * @param corpus corpo da mensagem
	 * @return uma HuffmanTree
	 */
	private static HuffmanTree treeFromCorpus(String corpus) {
		return new HuffmanTree(frequencyTable(corpus));
	}
	
	/**
	 * Devolve a tabela de frequEncias absolutas de ocorrência 
	 * de cada carActer no corpus passado como argumento
	 * 
	 * @param corpus corpo da mensagem
	 * @return tabela de frequEncias
	 */
	private static HashMap<Character, Integer> frequencyTable(String corpus) {
		HashMap<Character, Integer> table = new HashMap<>();
		
		//itera pelas caracteres do corpus
		for (int i = 0; i < corpus.length(); i++) {
			char Key = corpus.charAt(i);
			
			//caso não exista Key
			if (!table.containsKey(Key)) {
				table.put(Key, 1);
			}
			//caso exista Key
			else {
				Integer frequency = table.get(Key);
				frequency++;
				table.put(Key, frequency);
			}
		}
		return table;
	}
	
	/**
	 * Classe HuffmanTree
	 * 
	 * @author Afonso Santos - fc59808
	 *
	 */
	private static class HuffmanTree {

		private HuffmanNode root;
		
		/**
		 * Cria uma árvore de Huffman a partir de uma tabela de frequências
		 * 
		 * @param frequencies tabela de frequEncias
		 */
		private HuffmanTree(HashMap<Character, Integer> frequencies) {
			PriorityQueue<HuffmanNode> organizedNodes = new PriorityQueue<>();
			
			//Itera pelas entries do HashMap, criando nodes e adicionando-as A PriorityQueue
			for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
				HuffmanNode node = new HuffmanNode(entry.getValue(), entry.getKey());
				
				organizedNodes.add(node);
			}
			//CriaCAo da BinaryTree
			while (!organizedNodes.isEmpty()){
				
				//caso exista mais do que um node
				if (organizedNodes.size() >= 2) {
					HuffmanNode left = organizedNodes.poll();
					HuffmanNode right = organizedNodes.poll();
					int sumFrequency = left.frequency + right.frequency;
					
					HuffmanNode newFather = new HuffmanNode(sumFrequency,left,right);
					
					organizedNodes.add(newFather);
				}
				//caso falta um node
				else {
					root = organizedNodes.poll();
				}
			}
		}
		
		/**
		 * Devolve os cOdigos da Arvore
		 * 
		 * @return os cOdigos da Arvore
		 */
		private HashMap<Character, String> getHuffmanCodes() {
			HashMap<Character, String> codes = new HashMap<>();

			getHuffmanCodesAux(root, "", codes);

			return codes;
		}
		
		/**
		 * Partir do nO raiz, faz uma travessia completa da Arvore, gerando cOdigos de
		 * Huffman (strings de 0s e 1s) por meio dessa travessia
		 * 
		 * @param node node da Arvore
		 * @param code cOdigo a ser gerado
		 * @param codes cOdigos gerados
		 */
		private void getHuffmanCodesAux(HuffmanNode node, String code, HashMap<Character, String> codes) {
			//caso seja uma folha (base)
			if (node.isLeaf()) {
				codes.put(node.c, code);
			}
			//caso recursivo
			else {	
				StringBuilder left = new StringBuilder(code);
				StringBuilder right = new StringBuilder(code);
				
				//caso tenha filho esquerdo
				if (node.left != null) {
					left.append("0");
					getHuffmanCodesAux(node.left,left.toString(),codes);
				}
				
				//caso tenha filho direito
				if (node.right != null) {
					right.append("1");
					getHuffmanCodesAux(node.right,right.toString(),codes);
				}
			}
		}
	}
	
	/**
	 * Classe HuffmanNode
	 * 
	 * @author Afonso Santos - fc59808
	 *
	 */
	private static class HuffmanNode implements Comparable<HuffmanNode> {

		int frequency;
		char c;
		HuffmanNode left;
		HuffmanNode right;

		/**
		 * Creates a leaf
		 * 
		 * @param frequency frequEncia
		 * @param c carActer
		 */
		private HuffmanNode(int frequency, char c) {
			this.frequency = frequency;
			this.c = c;
			// this.left not initialized; remains null
			// this.right not initialized; remains null
		}

		/**
		 * Creates an internal node
		 * 
		 * @param frequency frequEncia
		 * @param left node filho esquerdo
		 * @param right node filho direito
		 */
		private HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
			this.frequency = frequency;
			// no need to initialize this.c, because it is not used
			this.left = left;
			this.right = right;
		}
		
		/**
		 * Verifica se um node E folha ou nAo
		 * 
		 * @return true se for folha
		 * false se nAo for folha
		 */
		private boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int compareTo(HuffmanNode node) {
			return this.frequency - node.frequency;
		}
	}
}

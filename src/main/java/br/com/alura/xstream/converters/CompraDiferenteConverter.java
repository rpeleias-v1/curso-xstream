package br.com.alura.xstream.converters;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.alura.xstream.Compra;
import br.com.alura.xstream.Produto;

public class CompraDiferenteConverter implements Converter {

	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Compra.class);
	}

	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.addAttribute("estilo", "novo");
		Compra compra = (Compra)object;
		writer.startNode("id");
		context.convertAnother(compra.getId());
		writer.endNode();
		
		writer.startNode("fornecedor");
		writer.setValue("rpeleias@hotmail.com");
		writer.endNode();
		
		writer.startNode("endereco");
		writer.startNode("linha1");
		writer.setValue("Rua Tibiri 282");
		writer.endNode();		
		writer.startNode("linha2");
		writer.setValue("apto. 122 - São Paulo - SP");
		writer.endNode();
		writer.endNode();
		
		writer.startNode("produtos");
		context.convertAnother(compra.getProdutos());
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String estilo = reader.getAttribute("estilo");
		
		reader.moveDown();
		String nomeId = reader.getNodeName();
		String valorId = reader.getValue();
		reader.moveUp();
		
		reader.moveDown();
		String fornecedor = reader.getAttribute("fornecedor");
		reader.moveUp();
		
		reader.moveDown();
		String nomeTagEndereco = reader.getNodeName();
		reader.moveDown();
		String linha1 = reader.getValue();
		reader.moveUp();
		reader.moveDown();
		String linha2 = reader.getValue();
		reader.moveUp();
		reader.moveUp();
		
		List<Produto> produtos = new ArrayList<Produto>();
		int id = Integer.parseInt(valorId);
		Compra compra = new Compra(id, produtos);
		
		reader.moveDown();
		List<Produto> produtosConvertidos = (List<Produto>) context.convertAnother(compra, List.class);
		produtos.addAll(produtosConvertidos);
		reader.moveUp();

		return compra;
	}

}

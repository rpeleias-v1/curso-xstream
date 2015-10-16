package br.com.alura.xstream.converters;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PrecoConverter implements Converter {

	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Double.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Double valor = (Double)value;
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatador = NumberFormat.getCurrencyInstance(brasil);
		String valorEmReais = formatador.format(valor);
		writer.setValue(valorEmReais);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String valor = reader.getValue();
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatador = NumberFormat.getCurrencyInstance(brasil);
		try {
			return formatador.parse(valor);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

}

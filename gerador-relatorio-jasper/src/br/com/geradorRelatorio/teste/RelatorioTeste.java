package br.com.geradorRelatorio.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.geradorRelatorio.modelo.Cliente;
import br.com.geradorRelatorio.relatorio.RelatorioPiloto;
import br.com.geradorRelatorio.relatorio.TipoRelatorio;

public class RelatorioTeste {

	public static void main(String[] args) {
		try {
			Cliente Cliente1 = new Cliente();
			Cliente1.setNome("RD Tecnologia");
			Cliente1.setEndereco("Rua Guaranis, Ipatinga");
			Cliente1.setComplemento("Sala 105");
			Cliente1.setTelefone("8888-5566");
			Cliente1.setUf("MG");
			
			Cliente Cliente2 = new Cliente();
			Cliente2.setNome("Romero Gonçalves Dias");
			Cliente2.setEndereco("Av Uruguai, Belo Horizonte");
			Cliente2.setComplemento("3º Andar");
			Cliente2.setUf("MG");
			
			Cliente Cliente3 = new Cliente();
			Cliente3.setNome("FLC Tecnologia");
			Cliente3.setEndereco("Rua Aricanduva, São Paulo");
			Cliente3.setComplemento("Sala 23");
			Cliente1.setTelefone("98523-1234");
			Cliente3.setUf("SP");
			
			List<Cliente> clientes = new ArrayList<Cliente>();
			clientes.add(Cliente1);
			clientes.add(Cliente2);
			clientes.add(Cliente3);
			
			RelatorioPiloto relatorio = new RelatorioPiloto();
			relatorio.imprimir(clientes, TipoRelatorio.PDF);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

package br.com.geradorRelatorio.relatorio;

import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import br.com.geradorRelatorio.modelo.Cliente;

public class RelatorioPiloto {
	
	private static final String NOME_RELATORIO_JASPER = "relatorio-piloto.jasper";
	private static final String NOME_RELATORIO_PDF = "Relatorio_de_Clientes.pdf";
	private static final String NOME_RELATORIO_XLS = "Relatorio_de_Clientes.xls";
	private static final String PASTA_RELATOIOS = "C:/Users/a2hh/Development/Projects/relatorios/";
	
	private String path; //Caminho base
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	
	//Recupera os caminhos para que a classe possa encontrar os relatórios
	public RelatorioPiloto() {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "br/com/geradorRelatorio/relatorio/jasper/";
		System.out.println(path);
	}
	
	//Imprime/gera uma lista de Clientes
	public void imprimir(List<Cliente> clientes, TipoRelatorio tipo) throws Exception	
	{
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);
		
		switch (tipo) {
			case PDF:
				exportaPdf(dataSource);
				break;
			case XLS:
				exportaXls(dataSource);
				break;
		}
		
	}

	private void exportaXls(JRBeanCollectionDataSource dataSource)
			throws JRException {
		String printFileName = JasperFillManager
				.fillReportToFile(this.getPathToReportPackage() + NOME_RELATORIO_JASPER, null, dataSource);
		JRXlsExporter xlsExporter = new JRXlsExporter();
		xlsExporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, printFileName);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, PASTA_RELATOIOS + NOME_RELATORIO_XLS);
		xlsExporter.exportReport();
	}

	private void exportaPdf(JRBeanCollectionDataSource dataSource)
			throws JRException {
		JasperPrint print = JasperFillManager.fillReport(this.getPathToReportPackage() + NOME_RELATORIO_JASPER, null, dataSource);
		JasperExportManager.exportReportToPdfFile(print, PASTA_RELATOIOS + NOME_RELATORIO_PDF);
	}
	 
	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}
	
	public String getPath() {
		return this.path;
	}
}

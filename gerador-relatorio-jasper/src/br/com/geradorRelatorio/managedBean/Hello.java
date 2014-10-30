package br.com.geradorRelatorio.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import br.com.geradorRelatorio.modelo.Cliente;
import br.com.geradorRelatorio.relatorio.RelatorioPiloto;

@RequestScoped
@ManagedBean
public class Hello {
	
	private static final String NOME_RELATORIO_JASPER = "relatorio-piloto.jasper";
	private static final String NOME_RELATORIO_PDF = "Relatorio_de_Clientes.pdf";
	private static final String NOME_RELATORIO_XLS = "Relatorio_de_Clientes.xls";
	private static final String PASTA_RELATOIOS = "C:/Users/a2hh/Development/Projects/relatorios/";
	
	private String path; //Caminho base
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	
	private List<Cliente> clientes;

	@PostConstruct
	public void init() {
		System.out.println(" Bean executado! Ma OE!");
		
		this.path = RelatorioPiloto.class.getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "br/com/geradorRelatorio/relatorio/jasper/";
		
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
		
		clientes = new ArrayList<Cliente>();
		clientes.add(Cliente1);
		clientes.add(Cliente2);
		clientes.add(Cliente3);
	}

	public String getMessage() {
		return "Download do relatorio";
	}

	public void obterRelatorioPdf() {
		try {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);
			
			HttpServletResponse httpServletResponse = 
					(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperPrint jasperPrint=
					JasperFillManager.fillReport(pathToReportPackage + NOME_RELATORIO_JASPER, null, dataSource);  
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void obterRelatorioXls() {
		try {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);
			
			HttpServletResponse httpServletResponse = 
					(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperPrint jasperPrint=
					JasperFillManager.fillReport(pathToReportPackage + NOME_RELATORIO_JASPER, null, dataSource); 
			JRXlsxExporter xlsExporter = new JRXlsxExporter();
			xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					servletOutputStream);
			xlsExporter.exportReport();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

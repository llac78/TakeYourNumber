package com.llac.tyn.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.llac.tyn.model.GeneratedNumber;
import com.llac.tyn.service.GeneratedNumberService;

@Controller
public class GeneratedNumberController {

	private GeneratedNumberService generatedNumberService;

	@Autowired
	public GeneratedNumberController(GeneratedNumberService numberService) {
		this.generatedNumberService = numberService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/index")
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");

		GeneratedNumber number = new GeneratedNumber();
		mv.addObject("number_form", number);

		return mv;
	}

	@PostMapping()
	public ModelAndView save(@Valid @ModelAttribute("number_form") GeneratedNumber number, BindingResult result) throws IOException, DocumentException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");

		if (result.hasErrors()) {

			return mv;

		} else {

			Date generationDate = new Date();
			number.setGenerationDate(generationDate);

			generatedNumberService.save(number);

			Long lastNumber = number.getId();
			mv.addObject("lastNumber", lastNumber);
			
			this.createDocument(number);

			return mv;
		}
	}
	
	public void createDocument(GeneratedNumber number) throws FileNotFoundException, DocumentException {
		
        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream("D:\\GeneratedNumber_" + number.getId().toString() +".pdf"));
            document.open();

            document.add(new Paragraph("Number: " + number.getId().toString()));
            
            document.add(new Paragraph(this.getFormatedGenerationDate(number.getGenerationDate()), FontFactory.getFont(FontFactory.COURIER, 12)));

        } catch(DocumentException de) {
            System.err.println(de.getMessage());
        } catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            document.close();
        }
	}
	
	public String getFormatedGenerationDate(Date number) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String formatedDate = sdf.format(number);
		
		return formatedDate;
	}

}

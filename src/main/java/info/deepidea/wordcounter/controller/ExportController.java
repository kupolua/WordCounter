package info.deepidea.wordcounter.controller;

import info.deepidea.wordcounter.service.WordCounterResultContainer;
import info.deepidea.wordcounter.service.WordCounterService;
import info.deepidea.wordcounter.util.ErrorMessenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class ExportController {
    private final WordCounterService wordCounterService;

    @Autowired
    public ExportController(@Qualifier("wordCounterService") WordCounterService wordCounterService) {
        this.wordCounterService = wordCounterService;
    }

    @RequestMapping(value = "/downloadPDF",
                    method = RequestMethod.POST,
                    produces = "application/pdf;charset=UTF-8")
    public ModelAndView getPdfResult(@RequestParam String textCount,
                                     @RequestParam String sortingOrder,
                                     @RequestParam String isFilterWords,
                                     @RequestParam int crawlDepth,
                                     @RequestParam boolean crawlScope,
                                     HttpServletRequest clientHttpRequest) throws  Throwable {
        final String viewName = "pdfView";
        setErrorLocale(clientHttpRequest);
        CountWordsUserRequest request =
                new CountWordsUserRequestImpl(textCount, sortingOrder, isFilterWords, crawlDepth, crawlScope);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        ModelAndView modelAndView = getModelAndView(viewName, result);
        return modelAndView;
    }

    @RequestMapping(value = "/downloadExcel",
                    method = RequestMethod.POST,
                    produces = "application/vnd.ms-excel;charset=UTF-8")
    public ModelAndView getExcelResult(@RequestParam String textCount,
                                       @RequestParam String sortingOrder,
                                       @RequestParam String isFilterWords,
                                       @RequestParam int crawlDepth,
                                       @RequestParam boolean crawlScope,
                                       HttpServletRequest clientHttpRequest) throws Throwable {
        final String viewName = "excelView";
        setErrorLocale(clientHttpRequest);
        CountWordsUserRequest request =
                new CountWordsUserRequestImpl(textCount, sortingOrder, isFilterWords, crawlDepth, crawlScope);
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(request);

        ModelAndView modelAndView = getModelAndView(viewName, result);
        return modelAndView;
    }

    private void setErrorLocale(HttpServletRequest servletRequest) {
        Locale userLocale = servletRequest.getLocale();
        ErrorMessenger.setLocale(userLocale);
    }

    private ModelAndView getModelAndView(String viewName, WordCounterResultContainer result) {
        Map<String, Integer> resultMap = result.getCountedResult();
        List<String> errorList = result.getErrors();
        ModelAndView modelAndView = new ModelAndView(viewName, "calculatedWords", resultMap);
        modelAndView.addObject("errorList", errorList);
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView handleException(Throwable e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", e.getMessage());
        return model;
    }
}

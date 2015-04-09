package info.deepidea.wordcounter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/topWordsRelationDiagram")
public class TopWordsRelationDiagramController {
    @Value("${app.version}")
    private String version;

    @RequestMapping(method = RequestMethod.GET)
    public String about(ModelMap model) {
        model.addAttribute("version", version);
        return "topWordsRelationDiagram";
    }
}

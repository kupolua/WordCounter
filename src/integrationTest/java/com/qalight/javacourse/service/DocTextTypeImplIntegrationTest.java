package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DocTextTypeImplIntegrationTest {
    private DocTextTypeImpl docTextType;

    @Before
    public void setup() {
        docTextType = new DocTextTypeImpl();
    }

    @Test
    public void testIsEligible_invalidPdfType() {
        //given
        final String DATA_SOURCE_LINK = "http://defas.com.ua/java/Policy_of_.UA.pdf";
        //when
        boolean actualResult = docTextType.isEligible(DATA_SOURCE_LINK);
        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_validTypes() {
        //given
        final String[] SOURCE_LIST = {
        "http://www.snee.com/xml/xslt/sample.doc",
        "http://www.ojk.ee/sites/default/files/respondus-docx-sample-file_0.docx",
        "http://thewalter.net/stef/software/rtfx/sample.rtf",
        "http://www.openoffice.org/documentation/whitepapers/Creating_large_documents_with_OOo.odt",
        "http://www.econ.yale.edu/~shiller/data/ie_data.xls",
        "http://file-sample.com/wp-content/uploads/2013/07/Sample-Excel-Workbook-Document.xlsx",
        "http://www.unm.edu/~unmvclib/powerpoint/pptexamples.ppt",
        "https://sctcc.ims.mnscu.edu/shared/CheckYourComputer/SamplePPTX.pptx"
        };

        //when
        boolean actualResult = checkIfSourceIsEligible(SOURCE_LIST);

        //then
        final boolean expectedResult = true;
        Assert.assertEquals(expectedResult, actualResult);
    }

    private boolean checkIfSourceIsEligible(String[] list){
        boolean result = true;
        for(String link : list){
            if(!docTextType.isEligible(link)){
                result = false;
                break;
            }
        }
        return result;
    }

    @Test(expected = NullPointerException.class)
    public void testIsEligible_nullLink(){
        //given
        final String NULL_LINK = null;
        //when
        docTextType.isEligible(NULL_LINK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink(){
        //given
        final String EMPTY_LINK = "";
        //when
        docTextType.isEligible(EMPTY_LINK);
    }
}
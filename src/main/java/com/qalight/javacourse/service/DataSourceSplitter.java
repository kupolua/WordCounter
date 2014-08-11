package com.qalight.javacourse.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavelkulakovsky on 10.08.14.
 */
public class DataSourceSplitter {

    private List<String> splitedDataSource = new ArrayList<>();

    public void split(String dataSources) {
        splitedDataSource.add("http://www.eslfast.com/supereasy/se/supereasy006.htm");
        splitedDataSource.add("http://www.xmlfiles.com/examples/cd_catalog.xml");
    }

    public List<String> getSource() {
        return splitedDataSource;
    }
}

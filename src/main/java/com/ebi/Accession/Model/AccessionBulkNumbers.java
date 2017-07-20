package com.ebi.Accession.Model;

import java.util.List;

public class AccessionBulkNumbers {

    private List<String> bulkAccession;
    
    public void setbulkAccession(List<String> bulkAccession) {
        this.bulkAccession = bulkAccession;
    }

    public List<String> getbulkAccession() {
        return bulkAccession;
    }

    public String toString() {
        if (bulkAccession != null) {
            return bulkAccession.stream().reduce((i, j) -> i + ", " + j).orElse("");
        }
        return "";
    }

}

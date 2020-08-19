package ds.kontrolnaya.models;

public class ExcelData {
    public String bankClassNumber;
    public String opGBActive="-";
    public String opGBPassive="-";
    public String credit="-";
    public String debit="-";
    public String outBGActive="-";
    public String outGBPassive="-";

    public ExcelData(String bank, String classNumber, String parameter1, String parameter2) {
        this.bankClassNumber = bank+", class: "+classNumber;
        this.opGBActive = parameter1;
        this.opGBPassive = parameter2;
    }

    public ExcelData(String bank, String classNumber) {
        this.bankClassNumber = bank+", class: "+classNumber;
    }

    public static ExcelData newTurnoverData(String bank, String classNumber, String parameter1, String parameter2){
        ExcelData excelData=new ExcelData(bank, classNumber);
        excelData.setCredit(parameter1);
        excelData.setDebit(parameter2);
        return excelData;
    }
    public static ExcelData newOutgoingData(String bank, String classNumber, String parameter1, String parameter2){
        ExcelData excelData=new ExcelData(bank, classNumber);
        excelData.setOutBGActive(parameter1);
        excelData.setOutGBPassive(parameter2);
        return excelData;
    }

    public String getBankClassNumber() {
        return bankClassNumber;
    }

    public String getOpGBActive() {
        return opGBActive;
    }

    public String getOpGBPassive() {
        return opGBPassive;
    }

    public String getCredit() {
        return credit;
    }

    public String getDebit() {
        return debit;
    }

    public String getOutBGActive() {
        return outBGActive;
    }

    public String getOutGBPassive() {
        return outGBPassive;
    }

    public void setBankClassNumber(String bankClassNumber) {
        this.bankClassNumber = bankClassNumber;
    }

    public void setOpGBActive(String opGBActive) {
        this.opGBActive = opGBActive;
    }

    public void setOpGBPassive(String opGBPassive) {
        this.opGBPassive = opGBPassive;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public void setOutBGActive(String outBGActive) {
        this.outBGActive = outBGActive;
    }

    public void setOutGBPassive(String outGBPassive) {
        this.outGBPassive = outGBPassive;
    }

    @Override
    public String toString() {
        return "ExcelData{" +
                "bankClassNumber='" + bankClassNumber + '\'' +
                ", opGBActive='" + opGBActive + '\'' +
                ", opGBPassive='" + opGBPassive + '\'' +
                ", credit='" + credit + '\'' +
                ", debit='" + debit + '\'' +
                ", outBGActive='" + outBGActive + '\'' +
                ", outGBPassive='" + outGBPassive + '\'' +
                "}\n";
    }
}

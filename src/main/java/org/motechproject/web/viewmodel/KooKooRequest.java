package org.motechproject.web.viewmodel;

public class KooKooRequest {
    private String sid;
    private String cid;
    private String called_number;
    private String event;
    private String data;
    private String status;
    private String callduration;
    private String record_duration;
    private String total_call_duration;
    private String process;
    private String outbound_sid;
    private String circle;
    private String operator;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCalled_number() {
        return called_number;
    }

    public void setCalled_number(String called_number) {
        this.called_number = called_number;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCallduration() {
        return callduration;
    }

    public void setCallduration(String callduration) {
        this.callduration = callduration;
    }

    public String getRecord_duration() {
        return record_duration;
    }

    public void setRecord_duration(String record_duration) {
        this.record_duration = record_duration;
    }

    public String getTotal_call_duration() {
        return total_call_duration;
    }

    public void setTotal_call_duration(String total_call_duration) {
        this.total_call_duration = total_call_duration;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getOutbound_sid() {
        return outbound_sid;
    }

    public void setOutbound_sid(String outbound_sid) {
        this.outbound_sid = outbound_sid;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "KooKooRequest{" +
                "sid='" + sid + '\'' +
                ", cid='" + cid + '\'' +
                ", called_number='" + called_number + '\'' +
                ", event='" + event + '\'' +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                ", callduration='" + callduration + '\'' +
                ", record_duration='" + record_duration + '\'' +
                ", total_call_duration='" + total_call_duration + '\'' +
                ", process='" + process + '\'' +
                ", outbound_sid='" + outbound_sid + '\'' +
                ", circle='" + circle + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}

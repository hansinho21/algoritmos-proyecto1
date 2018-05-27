/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author hvill
 */
public class OrderPart2 {
    
    int idOrder;
    String agentName;
    String date;
    float totalOrder;
    String province;
    String address;
    String driverName;

    public OrderPart2() {
    }

    public OrderPart2(int idOrder, String agentName, String date, float totalOrder, String province, String address, String driverName) {
        this.idOrder = idOrder;
        this.agentName = agentName;
        this.date = date;
        this.totalOrder = totalOrder;
        this.province = province;
        this.address = address;
        this.driverName = driverName;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(float totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return "OrderPart2{" + "idOrder=" + idOrder + ", agentName=" + agentName + ", date=" + date + ", totalOrder=" + totalOrder + ", province=" + province + ", address=" + address + ", driverName=" + driverName + '}';
    }
    
}

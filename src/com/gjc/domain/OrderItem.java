package com.gjc.domain;

public class OrderItem {
    private int id;//订单条目编号
    private int buycount;//所购买商品数量
    private float total;//该商品总价格
    private Product product;//所购买商品
    private Order order;//所属订单

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuycount() {
        return buycount;
    }

    public void setBuycount(int buycount) {
        this.buycount = buycount;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", buycount=" + buycount +
                ", total=" + total +
                ", product=" + product +
                ", orderid=" + order.getOrderid() +
                '}';
    }
}

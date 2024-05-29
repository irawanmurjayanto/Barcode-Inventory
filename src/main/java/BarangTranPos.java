package com.example.barcodegcnew;

import java.io.Serializable;

public class BarangTranPos implements Serializable {

        private int idno=0;

        private String item_code="";

        private String product="";

        private Double harga_jual=0.0;

        private Double qty=0.0;

        private Double discount=0.0;

        private Double subtot=0.0;

        private int qtyret=0;

    public int getQtyret() {
        return qtyret;
    }

    public void setQtyret(int qtyret) {
        this.qtyret = qtyret;
    }

    public int getIDNO() {

            return idno;

        }

        public void setIDNO(int idno) {

            this.idno = idno;

        }



        public String getItem_code() {

            return item_code;

        }

        public void setItem_code(String item_code) {

            this.item_code = item_code;

        }



        public String getProduct() {

            return product;

        }

        public void setProduct(String product) {

            this.product = product;

        }



        public Double getHarga_jual() {

            return harga_jual;

        }

        public void setHarga_jual(Double harga_jual) {

            this.harga_jual = harga_jual;

        }

        public Double getQty() {

            return qty;

        }

        public void setQty(Double qty) {

            this.qty = qty;

        }

    public Double getDiscount() {

        return discount;

    }

    public void setDiscount(Double discount) {

        this.discount = discount;

    }

    public Double getSubtot() {

        return subtot;

    }

    public void setSubtot(Double subtot) {

        this.subtot = subtot;

    }

/*
        @Override

        public String toString() {

            return "Barang{" +
                    "id='" + id + '\'' +
                    ", kode='" + kode + '\'' +
                    ", nama='" + nama + '\'' +
                    ", harga='" + harga + '\'' +
                    ", harga_beli='" + harga_beli + '\'' +
                    '}';

        }
*/
    }




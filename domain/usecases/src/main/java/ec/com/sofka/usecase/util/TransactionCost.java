package ec.com.sofka.usecase.util;

public enum TransactionCost {
    DEPOSITO_SUCURSAL(0.0),
    DEPOSITO_CAJERO(2.0),
    DEPOSITO_OTRA_CUENTA(1.5),
    COMPRA_FISICA(0.0),
    COMPRA_WEB(5.0),
    RETIRO_CAJERO(1.0);

    private final double costo;

    TransactionCost(double costo) {
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }
}

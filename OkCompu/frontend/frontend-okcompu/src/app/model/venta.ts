import { Almacen } from "./almacen";
import { Cliente } from "./cliente";
import { DetalleVenta } from "./detalle-venta";
import { Empleado } from "./empleado";
import { FormaPago } from "./forma-pago";
import { Igv } from "./igv";
import { Moneda } from "./moneda";



export class Venta {  
    idVenta?: number;  
    fecha?: string;  
    total?: number;  
    empleado?: Empleado;  
    almacen?: Almacen;  
    cliente?: Cliente;  
    idComprobante?: number;  
    serie?: string;  
    numero?: string;  
    moneda?: Moneda;  
    formaPago?: FormaPago;  
    igv?: Igv;  
    detalleVentas?: DetalleVenta[];  
}
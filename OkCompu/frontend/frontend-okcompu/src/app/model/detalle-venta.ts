import { Producto } from "./producto";

export interface DetalleVenta {  
    idDetalleVenta?: number;  
    producto?: Producto;  
    cantidad?: number;  
    precio?: number;  
    descuento?: number;  
    subTotal?: number;  
}
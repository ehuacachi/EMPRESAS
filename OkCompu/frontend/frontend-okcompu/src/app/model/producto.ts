import { Categoria } from "./categoria";
import { Marca } from "./marca";
import { UnidadMedida } from "./unidad-medida";

export class Producto {
    idProducto: number;
    descripcion: string;    
    modelo: string;
    code: string;
    urlImage: string;
    precioCompra: number;
    precioVenta: number;
    utilidad: number;
    margenUtilidad: number;
    estado: number;
    marca: Marca;
    unidadMedida: UnidadMedida;
    categoria: Categoria;
}

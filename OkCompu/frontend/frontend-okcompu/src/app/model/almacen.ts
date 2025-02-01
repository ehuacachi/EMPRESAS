import { Domicilio } from "./domicilio";
import { Telefono } from "./telefono";

export class Almacen {
    idAlmacen?: number;
    descripcion: string;
    telefonos: Telefono[];
    domicilios: Domicilio[];
}
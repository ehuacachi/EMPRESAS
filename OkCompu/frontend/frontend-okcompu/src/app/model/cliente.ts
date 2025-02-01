import { Domicilio } from "./domicilio";
import { Telefono } from "./telefono";
import { TipoDocumento } from "./tipo-documento";

// export interface Cliente {
export class Cliente {
    idCliente?: number;
    nombres: string;
    apellidos: string;
    tipoDocumento: TipoDocumento;
    nroDocumento: string;
    email: string;
    foto?: string;
    estado: number;
    telefonos: Telefono[];
    domicilios: Domicilio[];
}
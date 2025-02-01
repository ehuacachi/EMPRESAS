import { Rol } from "./rol";

export interface Menu {  
    idMenu?: number;  
    nombre: string;  
    url: string;  
    icono?: string;  
    activo?: boolean;  
    roles?: Rol[];  
  }  
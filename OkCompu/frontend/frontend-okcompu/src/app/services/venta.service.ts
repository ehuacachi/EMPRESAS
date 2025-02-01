import { Injectable } from '@angular/core';
import { Venta } from '../model/venta';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { GenericService } from './generic.service';
import { VentaFiltro } from '../model/venta-filtro';

@Injectable({
  providedIn: 'root'
})
export class VentaService extends GenericService<Venta> {

  constructor(protected override http: HttpClient) {
      super(http, `${environment.HOST}/ventas`)
    }

    buscarPorFechas(filtro: VentaFiltro) {  
      return this.http.get<Venta[]>(`${this.url}/fechas`, {  
        params: {  
          fechaInicio: filtro.fechaInicio,  
          fechaFin: filtro.fechaFin  
        }  
      });  
    }  
  
    buscarPorCliente(idCliente: number) {  
      return this.http.get<Venta[]>(`${this.url}/cliente/${idCliente}`);  
    } 

}

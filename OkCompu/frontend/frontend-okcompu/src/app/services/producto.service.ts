import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { Producto } from '../model/producto';
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ProductoService extends GenericService<Producto> {
  private productoChange: Subject<Producto[]> = new Subject<Producto[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/productos`)
  }

  buscarPorNombre(termino: string) {  
    return this.http.get<Producto[]>(`${this.url}/buscar`, {  
      params: { termino }  
    });  
  }  

  verificarStock(idProducto: number, idAlmacen: number) {  
    return this.http.get<number>(`${this.url}/stock/${idProducto}/${idAlmacen}`);  
  }

  setProductoChange(data: Producto[]){
    this.productoChange.next(data);
  }

  getProductoChange(){
    return this.productoChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }

}

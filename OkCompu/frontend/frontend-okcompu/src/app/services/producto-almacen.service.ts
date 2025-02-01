import { Injectable } from '@angular/core';
import { ProductoAlmacen } from '../model/producto-almacen';
import { BehaviorSubject, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { GenericService } from './generic.service';

@Injectable({
  providedIn: 'root'
})
export class ProductoAlmacenService extends GenericService<ProductoAlmacen>{

  private productoAlmacenChange: Subject<ProductoAlmacen[]> = new Subject<ProductoAlmacen[]>;
  private messageChange: Subject<string> = new Subject<string>;
  private productoAlmacenSubject = new BehaviorSubject<ProductoAlmacen | null>(null);
  

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/productoAlmacens`)
  } 

  findByProducto(idProducto: number) {  
    return this.http.get<ProductoAlmacen[]>(`${this.url}/codProducto/${idProducto}`);  
  }  

  findByAlmacen(idAlmacen: number) {  
    return this.http.get<ProductoAlmacen[]>(`${this.url}/codAlmacen/${idAlmacen}`);  
  }  

  findByProductoAndAlmacen(idProducto: number, idAlmacen: number) {  
    return this.http.get<ProductoAlmacen>(`${this.url}/stock/producto/${idProducto}/almacen/${idAlmacen}`  
    );  
  }  

  updateRelacion(idProducto: number, idAlmacen: number, productoAlmacen: ProductoAlmacen) {  
    return this.http.put<ProductoAlmacen>(`${this.url}/producto/${idProducto}/almacen/${idAlmacen}`, productoAlmacen);  
  }  

  updateStock(idProducto: number, idAlmacen: number, cantidad: number) {  
    return this.http.put<ProductoAlmacen>(  
      `${this.url}/stock/producto/${idProducto}/almacen/${idAlmacen}?cantidad=${cantidad}`, null);  
  }  

  deleteRelacion(idProducto: number, idAlmacen: number) {  
    return this.http.delete<void>(`${this.url}/producto/${idProducto}/almacen/${idAlmacen}`);  
  }  


  //Metodo para el estado 
  setProductoAlmacenChange(data: ProductoAlmacen[]) {
    this.productoAlmacenChange.next(data);
  }
  
  getProductoAlmacenChange() {
    return this.productoAlmacenChange.asObservable();
  }
  
  setMessageChange(data: string) {
    this.messageChange.next(data);
  }
  
  getMessageChange() {
    return this.messageChange.asObservable();      
  }

  selectProductoAlmacen(productoAlmacen: ProductoAlmacen | null) {  
    this.productoAlmacenSubject.next(productoAlmacen);  
  }  

  getSelectProductoAlmacen(){
    return this.productoAlmacenSubject.asObservable();
  }
  
  // refreshList() {  
  //   this.refreshListSubject.next(true);  
  // }  
}

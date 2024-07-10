
import { Injectable } from '@angular/core';
import { Moneda } from '../model/moneda';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';
//En automatico Angular se cambia a de produccion
import { environment } from '../../environments/environment.development';
import { GenericService } from './generic.service';

@Injectable({
  providedIn: 'root'
})
export class MonedaService extends GenericService<Moneda>{

  // private url: string = `${environment.HOST}/monedas`;

  // Para enviar informacion a otro componente.
  private monedaChange: Subject<Moneda[]> = new Subject<Moneda[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(http: HttpClient) {
    super(http, `${environment.HOST}/monedas`)
  }

  // constructor(private http: HttpClient) { }  

  // findAll() {
  //   return this.http.get<Moneda[]>(this.url);
  // }

  // findBydId(id: number) {
  //   return this.http.get<Moneda>(`${this.url}/${id}`);

  // }

  // save(moneda: Moneda){
  //   return this.http.post(this.url, moneda);
  // }

  // update(id: number, moneda: Moneda){
  //   return this.http.put(`${this.url}/${id}`, moneda);
  // }

  // delete(id: number){
  //   return this.http.delete(`${this.url}/${id}`);
  // }

  //////////////////////////////////
  setMonedaChange(data: Moneda[]){
    this.monedaChange.next(data);
  }

  getMonedaChange(){
    return this.monedaChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }



}

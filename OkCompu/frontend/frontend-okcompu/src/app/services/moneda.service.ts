
import { Injectable } from '@angular/core';
import { Moneda } from '../model/moneda';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MonedaService {

  private url: string = `http://localhost:8085/monedas`;

  // Para enviar informacion a otro componente.
  private monedaChange: Subject<Moneda[]> = new Subject<Moneda[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(private http: HttpClient) { }

  

  findAll() {
    return this.http.get<Moneda[]>(this.url);
  }

  findBydId(id: number) {
    return this.http.get<Moneda>(`${this.url}/${id}`);

  }

  save(moneda: Moneda){
    return this.http.post(this.url, moneda);
  }

  update(id: number, moneda: Moneda){
    return this.http.put(`${this.url}/${id}`, moneda);
  }

  delete(id: number){
    return this.http.delete(`${this.url}/${id}`);
  }

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

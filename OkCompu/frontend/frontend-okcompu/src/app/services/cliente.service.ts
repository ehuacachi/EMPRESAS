import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { Cliente } from '../model/cliente';
import { BehaviorSubject, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends GenericService<Cliente> {

  private clienteChange: Subject<Cliente[]> = new Subject<Cliente[]>;
  private messageChange: Subject<string> = new Subject<string>;
  private clienteSubject: BehaviorSubject<Cliente | null>  = new BehaviorSubject<Cliente | null>(null);

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/clientes`)
  }

  buscarPorNombre(termino: string) {  
    return this.http.get<Cliente[]>(`${this.url}/buscar`, {  
      params: { termino }  
    });  
  }


  selectCliente(cliente: Cliente | null) {
    this.clienteSubject.next(cliente);
  }

  getSelectCliente() {
    return this.clienteSubject.asObservable();
  }

  setClienteChange(data: Cliente[]){
    this.clienteChange.next(data);
  }

  getClienteChange(){
    return this.clienteChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}

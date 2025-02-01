import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { Almacen } from '../model/almacen';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlmacenService extends GenericService<Almacen> {

  private almacenChange: Subject<Almacen[]> = new Subject<Almacen[]>;
  private messageChange: Subject<string> = new Subject<string>;
  private almacenSubject = new BehaviorSubject<Almacen | null>(null);
  

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/almacenes`)
  }

  selectAlmacen(almacen: Almacen | null) {
    this.almacenSubject.next(almacen);
  }

  getSelectAlmacen() {
    return this.almacenSubject.asObservable();
  }

  setAlmacenChange(data: Almacen[]) {
    this.almacenChange.next(data);
  }

  getAlmacenChange() {
    return this.almacenChange.asObservable();
  }

  setMessageChange(data: string) {
    this.messageChange.next(data);
  }

  getMessageChange() {
    return this.messageChange.asObservable();
  }


}

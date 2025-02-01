import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { UnidadMedida } from '../model/unidad-medida';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UnidadMedidaService extends GenericService<UnidadMedida>{

  private unidadMedidaChange: Subject<UnidadMedida[]> = new Subject<UnidadMedida[]>;
  private messageChange: Subject<string> = new Subject<string>;
  
  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/unidadMedidas`)
  }

  setUnidadMedidaChange(data: UnidadMedida[]){
    this.unidadMedidaChange.next(data);
  }

  getUnidadMedidaChange(){
    return this.unidadMedidaChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}

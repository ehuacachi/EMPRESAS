import { Injectable } from '@angular/core';
import { TipoDocumento } from '../model/tipo-documento';
import { GenericService } from './generic.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TipoDocumentoService extends GenericService<TipoDocumento> {

  private tipoDocumentoChange: Subject<TipoDocumento[]> = new Subject<TipoDocumento[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/tipoDocumentos`)
  }

  setTipoDocumentoChange(data: TipoDocumento[]){
    this.tipoDocumentoChange.next(data);
  }

  getTipoDocumentoChange(){
    return this.tipoDocumentoChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}

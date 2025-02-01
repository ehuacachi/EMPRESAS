import { Injectable } from '@angular/core';
import { Categoria } from '../model/categoria';
import { GenericService } from './generic.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService extends GenericService<Categoria> {

  private categoriaChange: Subject<Categoria[]> = new Subject<Categoria[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/categorias`)
  }

  setCategoriaChange(data: Categoria[]){
    this.categoriaChange.next(data);
  }

  getCategoriaChange(){
    return this.categoriaChange.asObservable();
  }

  setMessageChange(data: string){
    this.messageChange.next(data);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}

import { Injectable } from '@angular/core';
import { Categoria } from '../model/categoria';
import { GenericService } from './generic.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService extends GenericService<Categoria> {

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/categorias`)
  }
}

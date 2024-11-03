import { Injectable } from '@angular/core';
import { GenericService } from './generic.service';
import { Igv } from '../model/igv';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class IgvService extends GenericService<Igv> {

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/igvs`)
  }
}

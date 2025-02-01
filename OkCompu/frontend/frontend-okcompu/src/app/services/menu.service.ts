import { Injectable } from '@angular/core';
import { Menu } from '../model/menu';
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { GenericService } from './generic.service';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class MenuService extends GenericService<Menu> {

  private menuChange: Subject<Menu[]> = new Subject<Menu[]>;
  private messageChange: Subject<string> = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http, `${environment.HOST}/menus`)
  }

  listarActivos() {  
    return this.http.get<Menu[]>(`${this.url}/activos`);  
  }

  setMenuChange(data: Menu[]) {
    this.menuChange.next(data);
  }

  getMenuChange() {
    return this.menuChange.asObservable();
  }

  setMessageChange(data: string) {
    this.messageChange.next(data);
  }

  getMessageChange() {
    return this.messageChange.asObservable();
  }
}

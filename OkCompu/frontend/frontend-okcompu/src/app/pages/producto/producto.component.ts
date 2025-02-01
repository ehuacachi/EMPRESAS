import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Producto } from '../../model/producto';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { ProductoService } from '../../services/producto.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { switchMap } from 'rxjs';
import { MaterialModule } from '../../material/material.module';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [MaterialModule, RouterLink, RouterOutlet],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css'
})
export class ProductoComponent implements OnInit{
  dataSource: MatTableDataSource<Producto>;
  
  columnDefinitions = [
    { def: 'idProducto', label: 'ID', hide: true},
    { def: 'descripcion', label: 'Descripcion', hide: false},
    { def: 'modelo', label: 'Modelo', hide: false},
    { def: 'code', label: 'Code', hide: false},
    { def: 'urlImage', label: 'UrlImage', hide: false},
    { def: 'precioCompra', label: 'PrecioCompre', hide: false},
    { def: 'precioVenta', label: 'PrecioVenta', hide: false},
    { def: 'utilidad', label: 'Utilidad', hide: false},
    { def: 'margenUtilidad', label: 'MargenUtilidad', hide: false},
    { def: 'marca', label: 'Marca', hide: false},
    { def: 'categoria', label: 'Categoria', hide: false},
    { def: 'unidadMedida', label: 'UnidadMedida', hide: false},    
    { def: 'estado', label: 'Estado', hide: false},
    { def: 'acccion', label: 'Acccion', hide: false}
  ]
  //displayedColumns: string[] = [ 'idPatient', 'firstName', 'lastName', 'dni', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private productoService: ProductoService,    
    private _snackBar: MatSnackBar,
    private route: ActivatedRoute //Tambien me sirme para concoer si tiene activo la ruta hija
    
  ){}

  ngOnInit(): void {
    this.productoService.findAll().subscribe(data => {
      this.createTable(data);
    });
    
    this.productoService.getProductoChange().subscribe(data => {
      this.createTable(data);
    });

    this.productoService.getMessageChange().subscribe(data => {
      this._snackBar.open(data, 'INFO', {duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'});
    });
  }

  createTable(data: Producto[]){
    this.dataSource = new MatTableDataSource(data);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim();
  }

  getDisplayedColumns() {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  delete(idProducto: number){
    this.productoService.delete(idProducto)
    .pipe(switchMap(() => this.productoService.findAll() ))
    .subscribe(data => {
      this.productoService.setProductoChange(data);
      this.productoService.setMessageChange("DELETED!");
    });
  }

  checkChildren(){    
    return this.route.children.length > 0;
  }


}

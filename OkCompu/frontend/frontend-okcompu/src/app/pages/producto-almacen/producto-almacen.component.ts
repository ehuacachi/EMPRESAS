import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { MatTableDataSource } from '@angular/material/table';
import { ProductoAlmacen } from '../../model/producto-almacen';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ProductoAlmacenService } from '../../services/producto-almacen.service';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { switchMap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-producto-almacen',
  standalone: true,
  imports: [MaterialModule, RouterOutlet, RouterLink],
  templateUrl: './producto-almacen.component.html',
  styleUrl: './producto-almacen.component.css'
})
export class ProductoAlmacenComponent implements OnInit{
  displayedColumns: string[] = ['producto', 'almacen', 'cantidad', 'acciones'];  
  dataSource = new MatTableDataSource<ProductoAlmacen>();  

  @ViewChild(MatPaginator) paginator!: MatPaginator;  
  @ViewChild(MatSort) sort!: MatSort;  

  constructor(
    private productoAlmacenService: ProductoAlmacenService,
    private router: Router,
    private route: ActivatedRoute, //Tambien me ayuda para conocer si tiene activo la ruta hija
    private _snackBar: MatSnackBar
    ) {}  

  ngOnInit(): void {  
    this.loadData();  
    
    this.productoAlmacenService.getProductoAlmacenChange().subscribe(data => {         
        this.createTable(data);       
    }); 
    
    this.productoAlmacenService.getMessageChange().subscribe(data => {
      this._snackBar.open(data, 'INFO', {duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'});
    })

    // Definir la función de filtrado personalizada  
    this.dataSource.filterPredicate = (data: ProductoAlmacen, filter: string) => {
      const searchText = filter.toLowerCase();

      return data.producto.descripcion.toLowerCase().includes(searchText) || // Filtrar por descripción del producto  
        data.producto.idProducto.toString().includes(searchText) ||      // Filtrar por código del producto  
        data.almacen.descripcion.toLowerCase().includes(searchText) ||  // Filtrar por descripción del almacén  
        data.almacen.idAlmacen.toString().includes(searchText) ||      // Filtrar por código del almacén  
        data.cantidad.toString().includes(searchText);                 // Filtrar por cantidad  
    };
  }
  
  createTable(data: ProductoAlmacen[]) {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
  }

  loadData() {  
    this.productoAlmacenService.findAll().subscribe(data => {  
        this.createTable(data);
      });  
  }    

  onEdit(element: ProductoAlmacen) {
    
    this.productoAlmacenService.selectProductoAlmacen(element);
    this.router.navigate(['pages/productoAlmacen/productoAlmacenEdit']);        
  }  

  onDelete(element: ProductoAlmacen) {  
    if (confirm('¿Está seguro de eliminar este registro?')) {  
      this.productoAlmacenService.deleteRelacion(element.producto.idProducto, element.almacen.idAlmacen)
      .pipe(switchMap(() => this.productoAlmacenService.findAll()))
      .subscribe(data => {  
        this.productoAlmacenService.setProductoAlmacenChange(data);  
        this.productoAlmacenService.setMessageChange('Eliminado!');  
      });  
    }  
  }  

  applyFilter(e : any){
    this.dataSource.filter = e.target.value.trim().toLowerCase();
    
    // Con esta verificación  
    if (this.dataSource.paginator) {  
      this.dataSource.paginator.firstPage(); // Asegura que el usuario vea los resultados filtrados  
    } 
  }
  
  checkChildren(){    
    return this.route.children.length > 0;
  }
}

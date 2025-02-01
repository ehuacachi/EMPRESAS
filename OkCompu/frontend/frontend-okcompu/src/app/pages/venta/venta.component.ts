import { Component, inject, OnInit, signal } from '@angular/core';
import { Venta } from '../../model/venta';
import { VentaService } from '../../services/venta.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MaterialModule } from '../../material/material.module';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { ConfirmDialogComponent } from '../../shared/components/confirm-dialog/confirm-dialog.component';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-venta',
  standalone: true,
  imports: [MaterialModule, RouterLink, RouterOutlet],
  templateUrl: './venta.component.html',
  styleUrl: './venta.component.css'
})
export class VentaComponent implements OnInit {
  // Uso de signal para estado reactivo  
  private ventaService = inject(VentaService);  
  private dialog = inject(MatDialog);  
  private snackBar = inject(MatSnackBar);  
  private route = inject(ActivatedRoute); //Tambien me sirme para concoer si tiene activo la ruta hija

  // Uso de MatTableDataSource para mejor manejo de datos  
  dataSource = new MatTableDataSource<Venta>([]);  
  
  // Columnas definidas como señal para flexibilidad  
  displayedColumns = signal([  
    'idVenta',   
    'fecha',   
    'cliente',   
    'total',   
    'acciones'  
  ]);  

  // Estado de carga  
  isLoading = signal(false);  

  ngOnInit(): void {  
    this.loadVentas();  
  }  

  loadVentas(): void {  
    this.isLoading.set(true);  
    this.ventaService.findAll().subscribe({  
      next: (data) => {  
        this.dataSource.data = data;  
        this.isLoading.set(false);  
      },  
      error: (error) => {  
        console.error('Error al cargar ventas', error);  
        this.showMessage('Error al cargar las ventas');  
        this.isLoading.set(false);  
      }  
    });  
  }  

  onDelete(id: number): void {  
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {  
      data: {   
        message: '¿Está seguro de eliminar esta venta?',  
        title: 'Confirmar Eliminación'  
      }  
    });  

    dialogRef.afterClosed().subscribe(result => {  
      if (result) {  
        this.isLoading.set(true);  
        this.ventaService.delete(id).subscribe({  
          next: () => {  
            this.loadVentas();  
            this.showMessage('Venta eliminada correctamente', "success");  
          },  
          error: (error) => {  
            console.error('Error al eliminar venta', error);  
            this.showMessage('Error al eliminar la venta');  
            this.isLoading.set(false);  
          }  
        });  
      }  
    });  
  }  

  // Método para aplicar filtro  
  applyFilter(event: Event): void {  
    const filterValue = (event.target as HTMLInputElement).value;  
    this.dataSource.filter = filterValue.trim().toLowerCase();  
  }  

  private showMessage(message: string, type: 'success' | 'error' = 'error'): void {  
    this.snackBar.open(message, 'Cerrar', {  
      duration: 3000,  
      horizontalPosition: 'right',  
      verticalPosition: 'top',  
      panelClass: type === 'success'   
        ? ['success-snackbar']   
        : ['error-snackbar']  
    });  
  }  

  checkChildren(){    
    return this.route.children.length > 0;
  }
}  
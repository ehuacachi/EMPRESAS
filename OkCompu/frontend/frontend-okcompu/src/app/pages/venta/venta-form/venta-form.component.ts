import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { VentaService } from '../../../services/venta.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MaterialModule } from '../../../material/material.module';
import { ClienteService } from '../../../services/cliente.service';
import { AlmacenService } from '../../../services/almacen.service';
import { ProductoService } from '../../../services/producto.service';
import { catchError, debounceTime, distinctUntilChanged, EMPTY, finalize, forkJoin, Observable, of, switchMap } from 'rxjs';
import { Cliente } from '../../../model/cliente';
import { Producto } from '../../../model/producto';
import { Almacen } from '../../../model/almacen';
import { Empleado } from '../../../model/empleado';
import { Moneda } from '../../../model/moneda';
import { FormaPago } from '../../../model/forma-pago';
import { Igv } from '../../../model/igv';
import { MonedaService } from '../../../services/moneda.service';
import { IgvService } from '../../../services/igv.service';
import { Venta } from '../../../model/venta';

@Component({
  selector: 'app-venta-form',
  standalone: true,
  imports: [MaterialModule, RouterModule, ReactiveFormsModule],
  templateUrl: './venta-form.component.html',
  styleUrl: './venta-form.component.css'
})
export class VentaFormComponent implements OnInit {
  // Signals  
  almacenes = signal<Almacen[]>([]);
  empleados = signal<Empleado[]>([]);
  monedas = signal<Moneda[]>([]);
  formasPago = signal<FormaPago[]>([]);
  igvs = signal<Igv[]>([]);

  isLoading = signal(false);
  form = signal<FormGroup | null>(null);

  // Computed para cálculo de total  
  totalVenta = computed(() => {
    const detallesControls = this.form()?.get('detalles') as FormArray;
    return detallesControls
      ? detallesControls.controls.reduce((total, control) =>
        total + (control.get('subtotal')?.value || 0), 0
      )
      : 0;
  });

  // Inyección de dependencias  
  private fb = inject(FormBuilder);
  private ventaService = inject(VentaService);
  private clienteService = inject(ClienteService);
  private almacenService = inject(AlmacenService);
  private productoService = inject(ProductoService);
  private monedaService = inject(MonedaService);
  private igvService = inject(IgvService);
  private snackBar = inject(MatSnackBar);

  constructor() {
    this.initForm();
  }

  ngOnInit(): void {
    this.cargarDatosCatalogo();
    this.setupFormListeners();
  }

  // Inicialización del formulario  
  private initForm(): void {
    const form = this.fb.group({
      fecha: [new Date().toISOString().split('T')[0], Validators.required],
      empleado: [null, Validators.required],
      almacen: [null, Validators.required],
      cliente: [null, Validators.required],
      comprobante: [null, Validators.required],
      serie: ['', Validators.required],
      numero: ['', Validators.required],
      moneda: [null, Validators.required],
      formaPago: [null, Validators.required],
      igv: [null, Validators.required],
      clienteSearch: [''],
      productoSearch: [''],
      detalles: this.fb.array([])
    });

    this.form.set(form);
  }

  // Getter para detalles  
  detalles = computed(() => {
    const formValue = this.form();
    return formValue ? formValue.get('detalles') as FormArray : null;
  });

  // Configuración de listeners  
  private setupFormListeners(): void {
    const detalles = this.detalles();
    if (detalles) {
      detalles.controls.forEach((control, index) => {
        control.get('cantidad')?.valueChanges.subscribe(() => {
          this.calcularSubtotal(index);
        });
      });
    }
  }

  // Carga de catálogos  
  private cargarDatosCatalogo(): void {
    this.isLoading.set(true);

    forkJoin({
      almacenes: this.almacenService.findAll(),
      monedas: this.monedaService.findAll(),
      igvs: this.igvService.findAll()
    }).pipe(
      catchError(error => {
        console.error('Error al cargar catálogos', error);
        this.mostrarError('Error al cargar datos iniciales');
        this.isLoading.set(false);
        return EMPTY;
      }),
      finalize(() => this.isLoading.set(false))
    ).subscribe({
      next: (resultado) => {
        this.almacenes.set(resultado.almacenes);
        this.monedas.set(resultado.monedas);
        this.igvs.set(resultado.igvs);
      }
    });
  }

  // Métodos de manejo de detalles  
  private agregarDetalle(producto: Producto): void {
    const detalleForm = this.fb.group({
      producto: [producto],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precio: [producto.precioVenta, Validators.required],
      subtotal: [producto.precioVenta]
    });

    const detalles = this.detalles();
    if (detalles) {
      detalles.push(detalleForm);
      this.calcularSubtotal(detalles.length - 1);
    }
  }

  calcularSubtotal(index: number): void {
    const detalles = this.detalles();
    if (detalles) {
      const detalle = detalles.at(index);
      const cantidad = detalle.get('cantidad')?.value || 0;
      const precio = detalle.get('precio')?.value || 0;
      detalle.patchValue({ subtotal: cantidad * precio }, { emitEvent: false });
    }
  }

  eliminarDetalle(index: number): void {
    const detalles = this.detalles();
    if (detalles) {
      detalles.removeAt(index);
    }
  }

  // Métodos de selección  
  onClienteSelected(cliente: Cliente): void {
    const form = this.form();
    if (form) {
      form.patchValue({
        cliente: cliente,
        clienteSearch: `${cliente.nombres} ${cliente.apellidos}`
      });
    }
  }

  onProductoSelected(producto: Producto | null): void {  
    const form = this.form();  
    if (!form || !producto) return;  
  
    const almacen = form.get('almacen')?.value;  
    if (!almacen) {  
      this.mostrarError('Seleccione primero un almacén');  
      return;  
    }  
  
    this.agregarDetalle(producto);  
    form.get('productoSearch')?.reset();  
  }   

  // Envío del formulario  
  onSubmit(): void {
    const form = this.form();
    if (!form) return;

    if (form.invalid) {
      form.markAllAsTouched();
      this.mostrarError('Por favor, complete todos los campos requeridos');
      return;
    }

    const detalles = this.detalles();
    if (!detalles || detalles.length === 0) {
      this.mostrarError('Debe agregar al menos un producto');
      return;
    }

    this.isLoading.set(true);
    const venta = this.prepararVenta();

    this.ventaService.save(venta).subscribe({
      next: () => {
        this.mostrarMensaje('Venta registrada correctamente');
        this.resetForm();
      },
      error: (err) => {
        console.error('Error al registrar venta', err);
        this.mostrarError('Error al registrar la venta');
        this.isLoading.set(false);
      }
    });
  }

  // Preparación de venta  
  private prepararVenta(): Venta {  
    const form = this.form();  
    if (!form) throw new Error('Formulario no inicializado');  
  
    const { fecha, empleado, almacen, cliente, comprobante, serie, numero, moneda, formaPago, igv } = form.value;  
  
    const detalles = this.detalles();  
    if (!detalles) throw new Error('Detalles no inicializados');  
  
    return {  
      fecha,  
      total: this.totalVenta(),  
      empleado,  
      almacen,  
      cliente,  
      idComprobante: comprobante?.idComprobante,  
      serie,  
      numero,  
      moneda,  
      formaPago,  
      igv,  
      detalleVentas: detalles.controls.map(control => ({  
        producto: control.get('producto')?.value,  
        cantidad: control.get('cantidad')?.value,  
        precio: control.get('precio')?.value  
      }))  
    };  
  }  

  // buscarProductos(termino: string): Observable<Producto[]> {  
  //   if (!termino || termino.trim() === '') {  
  //     return of([]);  
  //   }  
  //   return this.productoService.buscarPorTermino(termino).pipe(  
  //     catchError(() => {  
  //       this.mostrarError('Error al buscar productos');  
  //       return of([]);  
  //     })  
  //   );  
  // }  

  // Reseteo del formulario  
  resetForm(): void {
    const form = this.form();
    if (!form) return;

    form.reset({
      fecha: new Date().toISOString().split('T')[0]
    });

    const detalles = this.detalles();
    if (detalles) {
      while (detalles.length) {
        detalles.removeAt(0);
      }
    }

    this.isLoading.set(false);
  }

  // Métodos de notificación  
  private mostrarMensaje(mensaje: string): void {
    this.snackBar.open(mensaje, 'CERRAR', {
      duration: 3000,
      horizontalPosition: 'right',
      verticalPosition: 'top'
    });
  }

  private mostrarError(mensaje: string): void {
    this.snackBar.open(mensaje, 'CERRAR', {
      duration: 5000,
      horizontalPosition: 'right',
      verticalPosition: 'top',
      panelClass: ['error-snackbar']
    });
  }

  // Esta funcion es para usar FormControl dentro de un @For
  getFormControl(index: number, controlName: string): FormControl {  
    const detalles = this.detalles();  
    if (!detalles) return new FormControl();  
    const detalle = detalles.at(index);  
    if (!detalle) return new FormControl();  
    return detalle.get(controlName) as FormControl;  
  }  
} 
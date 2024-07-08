import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { MonedaService } from '../../../services/moneda.service';
import { Moneda } from '../../../model/moneda';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-moneda-edit',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, RouterLink],
  templateUrl: './moneda-edit.component.html',
  styleUrl: './moneda-edit.component.css'
})
export class MonedaEditComponent implements OnInit{ 

  constructor(
    private route: ActivatedRoute,
    private monedaService: MonedaService,
    private router: Router
  ){}
  // //private route = inject(ActivatedRoute);
  
  form: FormGroup;
  id: number;
  isEdit: boolean;

  ngOnInit(): void {
      this.form = new FormGroup({
        idMoneda: new FormControl(0),
        descripcion: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]),
        abreviatura: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(5)]),
        estado: new FormControl('', [Validators.required, Validators.maxLength(1)])

      });

      this.route.params.subscribe(data => {
        this.id = data['id'];
        // console.log(this.id)
        this.isEdit = data['id'] != null;
        this.initForm();
      });
  }

  initForm(){
    if(this.isEdit){
      //precargar los datos
      this.monedaService.findBydId(this.id).subscribe(data => {
        this.form = new FormGroup({
          idMoneda: new FormControl(data.idMoneda),
          descripcion: new FormControl(data.descripcion),
          abreviatura: new FormControl(data.abreviatura),
          estado: new FormControl(data.estado)
        });
      });
    }
  }

  // get f(){
  //   return this.form.controls;
  // }

  operate(){
    const moneda: Moneda = new Moneda();
    moneda.idMoneda = this.form.value['idMoneda'];
    //const x = this.form.controls['idPatient'].value;
    //cons y = this.form.get('idPatient').value;
    moneda.descripcion = this.form.value['descripcion'];
    moneda.abreviatura = this.form.value['abreviatura'];
    moneda.estado = this.form.value['estado'];   

    
    if(this.isEdit){
      //UPDATE
      //PRACTICA COMUN PERO NO IDEAL
      this.monedaService.update(this.id, moneda).subscribe( () => {
        this.monedaService.findAll().subscribe(data => {
          this.monedaService.setMonedaChange(data);
          this.monedaService.setMessageChange('UPDATED!');
        });
      });
    }else{
      //INSERT
      //PRACTICA IDEAL
      //pipe, se utiliza antes de suscribirse
      this.monedaService.save(moneda)
      .pipe(switchMap(  ()=> this.monedaService.findAll()  ))
      .subscribe(data => {
        this.monedaService.setMonedaChange(data);
        this.monedaService.setMessageChange('CREATED!');
      });
    }

    this.router.navigate(['pages/moneda']);
  };

}


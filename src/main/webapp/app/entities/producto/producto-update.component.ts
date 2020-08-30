import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IGenero } from 'app/shared/model/genero.model';
import { GeneroService } from 'app/entities/genero/genero.service';
import { ITalla } from 'app/shared/model/talla.model';
import { TallaService } from 'app/entities/talla/talla.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  generos: IGenero[];

  tallas: ITalla[];

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    generoId: [null, Validators.required],
    tallaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected generoService: GeneroService,
    protected tallaService: TallaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
    });
    this.generoService
      .query()
      .subscribe((res: HttpResponse<IGenero[]>) => (this.generos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.tallaService
      .query()
      .subscribe((res: HttpResponse<ITalla[]>) => (this.tallas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      codigo: producto.codigo,
      nombre: producto.nombre,
      generoId: producto.generoId,
      tallaId: producto.tallaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      codigo: this.editForm.get(['codigo']).value,
      nombre: this.editForm.get(['nombre']).value,
      generoId: this.editForm.get(['generoId']).value,
      tallaId: this.editForm.get(['tallaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackGeneroById(index: number, item: IGenero) {
    return item.id;
  }

  trackTallaById(index: number, item: ITalla) {
    return item.id;
  }
}

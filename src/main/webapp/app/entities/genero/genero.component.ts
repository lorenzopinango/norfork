import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGenero } from 'app/shared/model/genero.model';
import { GeneroService } from './genero.service';
import { GeneroDeleteDialogComponent } from './genero-delete-dialog.component';

@Component({
  selector: 'jhi-genero',
  templateUrl: './genero.component.html'
})
export class GeneroComponent implements OnInit, OnDestroy {
  generos: IGenero[];
  eventSubscriber: Subscription;

  constructor(protected generoService: GeneroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.generoService.query().subscribe((res: HttpResponse<IGenero[]>) => {
      this.generos = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGeneros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGenero) {
    return item.id;
  }

  registerChangeInGeneros() {
    this.eventSubscriber = this.eventManager.subscribe('generoListModification', () => this.loadAll());
  }

  delete(genero: IGenero) {
    const modalRef = this.modalService.open(GeneroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.genero = genero;
  }
}

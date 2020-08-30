import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NorforkTestModule } from '../../../test.module';
import { GeneroDeleteDialogComponent } from 'app/entities/genero/genero-delete-dialog.component';
import { GeneroService } from 'app/entities/genero/genero.service';

describe('Component Tests', () => {
  describe('Genero Management Delete Component', () => {
    let comp: GeneroDeleteDialogComponent;
    let fixture: ComponentFixture<GeneroDeleteDialogComponent>;
    let service: GeneroService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NorforkTestModule],
        declarations: [GeneroDeleteDialogComponent]
      })
        .overrideTemplate(GeneroDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GeneroDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeneroService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

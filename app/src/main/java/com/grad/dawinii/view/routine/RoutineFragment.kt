package com.grad.dawinii.view.routine

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.grad.dawinii.R
import com.grad.dawinii.adapter.MedicineRoutineRecyclerAdapter
import com.grad.dawinii.databinding.FragmentRoutineBinding
import com.grad.dawinii.model.entities.Medicine
import com.grad.dawinii.model.entities.Routine
import com.grad.dawinii.util.Prevalent
import com.grad.dawinii.util.makeToast
import com.grad.dawinii.viewModel.LocalViewModel
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RoutineFragment : Fragment() {
    private lateinit var adapter: MedicineRoutineRecyclerAdapter
    private lateinit var binding: FragmentRoutineBinding
    private lateinit var localViewModel: LocalViewModel
    private lateinit var medicines:MutableList<Medicine>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel = ViewModelProvider(this)[LocalViewModel::class.java]
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localViewModel.medicinesForRoutineMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                medicines = mutableListOf()
                medicines.addAll(it)
                adapter.setData(medicines)
            }
        }
        initView()
    }

    private fun initView() {
        binding.apply {
            tvRoutineName.text = currentRoutine.routineName
            tvRoutineType.text = currentRoutine.routineType
            tvStartDate.text = currentRoutine.routineStartDate
            tvEndDate.text = currentRoutine.routineEndDate
            extractPdfBtn.setOnClickListener {
                createPdf()
            }
        }
        setMedicinesRecycler()

    }

    @Throws(FileNotFoundException::class)
    private fun createPdf() {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(path, "${Prevalent.currentUser?.name}_${currentRoutine.routineName}.pdf")
        val os = FileOutputStream(file)
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(24f, 24f, 24f, 24f)
        document.strokeWidth = 3f

        val drawable = getDrawable(requireContext(), R.drawable.logo)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        image.setHorizontalAlignment(HorizontalAlignment.LEFT)

        val titleParagraph = Paragraph("Dawinii Routine").setBold().setFontSize(32f).setTextAlignment(
            TextAlignment.CENTER)
        val nameParagraph = Paragraph("Name : ${Prevalent.currentUser?.name}").setBold().setFontSize(24f).setTextAlignment(TextAlignment.LEFT)
        val ageParagraph = Paragraph("Age : ${Prevalent.currentUser?.age.toString()}").setBold().setFontSize(24f).setTextAlignment(TextAlignment.LEFT)
        val genderParagraph = Paragraph("Gender : ${Prevalent.currentUser?.gender}").setBold().setFontSize(24f).setTextAlignment(TextAlignment.LEFT)
        val emptyParagraph = Paragraph("\n").setBold().setFontSize(18f).setTextAlignment(TextAlignment.LEFT)
        val routineNameParagraph = Paragraph("Routine Name : ${currentRoutine.routineName}").setBold().setFontSize(18f).setTextAlignment(TextAlignment.LEFT)
        val typeParagraph = Paragraph("Routine Type : ${currentRoutine.routineType}").setBold().setFontSize(18f).setTextAlignment(TextAlignment.LEFT)
        val startDateParagraph = Paragraph("Routine Start Date : ${currentRoutine.routineStartDate}").setBold().setFontSize(18f).setTextAlignment(TextAlignment.LEFT)
        val endDateParagraph = Paragraph("Routine Start Date : ${currentRoutine.routineEndDate}").setBold().setFontSize(18f).setTextAlignment(TextAlignment.LEFT)
        val medicinesParagraph = Paragraph("Medicines").setBold().setFontSize(24f).setTextAlignment(TextAlignment.CENTER)

        val width = floatArrayOf(20f, 250f, 150f)
        val table = Table(width)
        table.setHorizontalAlignment(HorizontalAlignment.CENTER)

        table.addCell(Cell().add(Paragraph(" ")))
        table.addCell(Cell().add(Paragraph("Medicine Name").setBold().setFontSize(18f)))
        table.addCell(Cell().add(Paragraph("Dose/Day").setFontSize(18f)))
        medicines.forEach {
            table.addCell(Cell().add(Paragraph("${medicines.indexOf(it)}").setBold().setFontSize(18f)))
            table.addCell(Cell().add(Paragraph("${it.medicineName}").setFontSize(18f)))
            table.addCell(Cell().add(Paragraph("${it.dose * it.doseCount}").setFontSize(18f)))
        }

        document.add(image)
        document.add(titleParagraph)
        document.add(emptyParagraph)
        document.add(nameParagraph)
        document.add(ageParagraph)
        document.add(genderParagraph)
        document.add(emptyParagraph)
        document.add(routineNameParagraph)
        document.add(typeParagraph)
        document.add(startDateParagraph)
        document.add(endDateParagraph)
        document.add(emptyParagraph)
        document.add(medicinesParagraph)
        document.add(table)
        document.close()
        makeToast(requireContext(), "Pdf File Created")
    }

    private fun setMedicinesRecycler() {
        val medicinesRv = binding.medicinesRv
        medicinesRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = MedicineRoutineRecyclerAdapter(requireContext())
        medicinesRv.adapter = adapter
        setDataSource()
    }

    private fun setDataSource() {
        localViewModel.getMedicinesWithRoutineName(currentRoutine.routineName)
    }

    companion object {
        lateinit var currentRoutine: Routine
        @JvmStatic
        fun newInstance() =
            RoutineFragment().apply {
                arguments = Bundle().apply {
                }
            }

        fun getRoutine(routine: Routine) {
            currentRoutine = routine
        }
    }
}
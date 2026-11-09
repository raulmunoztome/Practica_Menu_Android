package cat.insmarianao

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var fotoGuardada: Bitmap? = null //PAra guardar la foto después de girar la pantalla
    private val tomarFoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            foto -> if (foto != null) {

        val raiz = findViewById<LinearLayout>(R.id.raiz)
        fotoGuardada = foto
        //pasamos la foto de la camara al fondo
        raiz.background = BitmapDrawable(resources, foto)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("foto", fotoGuardada)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        fotoGuardada = savedInstanceState?.getParcelable("foto")

        if (fotoGuardada != null) {
            val raiz = findViewById<LinearLayout>(R.id.raiz)
            raiz.background = BitmapDrawable(resources, fotoGuardada)
        }

        val myToolbar: Toolbar = findViewById(R.id.my_toolbar)

        setSupportActionBar(myToolbar) // activar Toolbar

        val btnOcultar: Button = findViewById(R.id.btnOcultar)
        val btnMostrar: Button = findViewById(R.id.btnMostrar)

        btnOcultar.setOnClickListener {
            myToolbar.visibility = View.GONE
        }
        btnMostrar.setOnClickListener {
            myToolbar.visibility = View.VISIBLE
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_one) {
            Toast.makeText(this, R.string.toast_option_one, Toast.LENGTH_SHORT).show()
            return true

        } else if (item.itemId == R.id.option_two) {
            Toast.makeText(this, R.string.toast_option_two, Toast.LENGTH_SHORT).show()
            return true

        } else if (item.itemId == R.id.action_1) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=" + Uri.encode("Institut Marianao, Sant Boi de Llobregat"))
            }
            startActivity(intent)
            return true

        } else if (item.itemId == R.id.action_2) {
            tomarFoto.launch(null)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
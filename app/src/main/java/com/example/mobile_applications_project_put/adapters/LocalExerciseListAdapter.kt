import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.GifEntity
import com.example.mobile_applications_project_put.functions.DbUtility.loadSavedExercises


class LocalExerciseListAdapter(private val context: Context) : RecyclerView.Adapter<LocalExerciseListAdapter.ExerciseViewHolder>() {

    private var exercises: List<Pair<Exercise, GifEntity?>> = emptyList()

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_body_part)
        val textView: TextView = itemView.findViewById(R.id.tv_body_part)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.body_parts, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentPair = exercises[position]
        val currentExercise = currentPair.first
        val currentGif = currentPair.second
        holder.textView.text = currentExercise.name
        if (currentGif?.gif != null) {
            Glide.with(context)
                .asGif()
                .load(currentGif.gif)
                .into(holder.imageView)
        } else {
            // Если gif не загрузился из базы данных, вы можете загрузить его по URL в качестве запасного варианта.
            Glide.with(context)
                .load(currentExercise.gifUrl)
                .into(holder.imageView)
        }
    }

    override fun getItemCount() = exercises.size

    fun setExercises(exercises: List<Pair<Exercise, GifEntity?>>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }
}

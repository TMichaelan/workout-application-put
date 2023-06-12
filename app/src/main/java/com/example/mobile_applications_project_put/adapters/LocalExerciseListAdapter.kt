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
import com.example.mobile_applications_project_put.functions.DbUtility.loadSavedExercises


class LocalExerciseListAdapter(private val context: Context) : RecyclerView.Adapter<LocalExerciseListAdapter.ExerciseViewHolder>() {

    private var exercises: List<Exercise> = emptyList()

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_body_part)
        val textView: TextView = itemView.findViewById(R.id.tv_body_part)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.body_parts, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val current = exercises[position]
        holder.textView.text = current.name
        Glide.with(context)
            .load(current.gifUrl)
            .into(holder.imageView)
    }

    override fun getItemCount() = exercises.size

    fun setExercises(exercises: List<Exercise>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }
}

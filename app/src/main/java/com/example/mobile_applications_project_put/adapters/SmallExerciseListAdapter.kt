package com.example.mobile_applications_project_put.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.fragments.HomeFragment
import com.example.mobile_applications_project_put.functions.UserUtility.isInternetAvailable

class SmallExerciseListAdapter (
    private var exerciseList: List<Exercise>,
    private val listener: HomeFragment
    ): RecyclerView.Adapter<SmallExerciseListAdapter.MyViewHolder>() {

        private val internet = isInternetAvailable(context = listener.requireContext())
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.small_exercises, parent, false)

            return MyViewHolder(itemView, listener, exerciseList)
        }

        override fun getItemCount(): Int {
            return exerciseList.size
        }


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currentExercise = exerciseList[position]

            if (!internet) {
                holder.progressBar.visibility = View.GONE // Скрыть прогресс-бар
                holder.imageId.setImageResource(R.drawable.no_inet) // Установить изображение "No Internet"
            } else {
                holder.progressBar.visibility = View.VISIBLE // Показать прогресс-бар
                Glide.with(holder.imageId)
                    .asBitmap()
                    .load(currentExercise.gifUrl)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.progressBar.visibility = View.GONE // Скрыть прогресс-бар в случае ошибки загрузки
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.progressBar.visibility = View.GONE // Скрыть прогресс-бар после успешной загрузки
                            return false
                        }
                    })
                    .into(holder.imageId)
            }
            holder.name.text = currentExercise.name.capitalize()
            holder.itemView.setOnClickListener { listener.onItemClick(currentExercise) }
        }


        interface OnItemClickListener {
            fun onItemClick(exercise: Exercise)
        }


        class MyViewHolder(
            itemView: View,
            private val listener: OnItemClickListener,
            private val exerciseList: List<Exercise>
        ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val imageId: ImageView = itemView.findViewById(R.id.img_body_part)
            val name: TextView = itemView.findViewById(R.id.tv_body_part)
            val progressBar: ProgressBar = itemView.findViewById(R.id.small_progress_bar)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                listener.onItemClick(exerciseList[adapterPosition])
            }
        }
    }
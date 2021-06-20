package com.rsschool.quiz.fragmentClasses

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes
import androidx.navigation.findNavController
import com.rsschool.quiz.applicationLogicClasses.Quiz
import com.rsschool.quiz.R
import com.rsschool.quiz.applicationLogicClasses.BundleValues
import com.rsschool.quiz.databinding.FragmentQuizQuestionBinding

class FragmentQuizQuestion : Fragment() {
    private var _quiz: Quiz? = null
    private val quiz get() = _quiz!!

    private var _binding: FragmentQuizQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _quiz = it.getParcelable(QUIZ)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setCustomTheme(questionN % themesList.size)
        _binding = FragmentQuizQuestionBinding.inflate(layoutInflater)
        fillFormQuestion()
        binding.toolbar.setOnClickListener {
            previousQuestion(it)
        }
        with(binding.previousButton) {
            isEnabled = questionN != 0
            setOnClickListener {
                previousQuestion(it)
            }
        }
        with(binding.nextButton) {
            setOnClickListener {
                nextQuestion(it)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            previousQuestion(requireView())
        }
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    private fun setCustomTheme(themeId: Int) {
        activity?.setTheme(themesList[themeId])
        val ta = obtainStyledAttributes(activity, themeId, R.styleable.Theme_Quiz)
        activity?.window?.statusBarColor = ta.getColor(R.styleable.Theme_Quiz_colorPrimaryDark, Color.WHITE)
    }

    private fun fillFormQuestion() {

        val toolBarView = binding.toolbar
        toolBarView.title =
            "${getString(R.string.title_question_word)} ${questionN + 1}/${quiz.getCountQuestions()}"
        if (questionN == 0) {
            toolBarView.navigationIcon = null
        }

        val question = quiz.getQuestions()[questionN]
        val questionTextView = binding.question
        questionTextView.text = question.questionText

        val nextButtonView = binding.nextButton
        nextButtonView.isEnabled = false
        val answerOptionsGroupView = binding.radioGroup
        for (answerOption in question.answerOptions) {
            val radioButtonView = RadioButton(this.context)
            radioButtonView.id = answerOption.id
            radioButtonView.text = answerOption.answerOptionText
            radioButtonView.textSize = getString(R.string.text_size_for_answer_option_text).toFloat()
            radioButtonView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (question.numberOfCheckedAnswerOption == answerOption.id) {
                radioButtonView.isChecked = true
                nextButtonView.isEnabled = true
            }
            answerOptionsGroupView.addView(radioButtonView)
        }

        if (questionN == quiz.getCountQuestions() - 1) {
            nextButtonView.text = getString(R.string.button_submit_text)
        } else {
            nextButtonView.text = getString(R.string.button_next_text)
        }

        answerOptionsGroupView.setOnCheckedChangeListener { _, checkedId ->
            nextButtonView.isEnabled = true
            question.numberOfCheckedAnswerOption = checkedId
        }
    }

    private fun previousQuestion(view: View) {
        if (questionN == 0) {
            return
        }
        val bundle = Bundle()
        bundle.putParcelable(QUIZ, quiz)
        questionN--
        view.findNavController().navigate(R.id.action_fragmentQuizQuestion_self, bundle)
    }

    private fun nextQuestion(view: View) {
        val bundle = Bundle()
        bundle.putParcelable(QUIZ, quiz)
        if (questionN == quiz.getCountQuestions() - 1) {
            setCustomTheme(0)
            questionN = 0
            view
                .findNavController()
                .navigate(
                    R.id.action_fragmentQuizQuestion_to_fragmentQuizResult,
                    bundle
                )
        } else {
            questionN++
            view.findNavController().navigate(R.id.action_fragmentQuizQuestion_self, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val QUIZ = BundleValues.QUIZ
        private var questionN = 0
        private val themesList = listOf(
            R.style.Theme_Quiz_First,
            R.style.Theme_Quiz_Second,
            R.style.Theme_Quiz_Third,
            R.style.Theme_Quiz_Fourth,
            R.style.Theme_Quiz_Fifth
        )
    }
}
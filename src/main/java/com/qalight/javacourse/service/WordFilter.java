package com.qalight.javacourse.service;

import com.qalight.javacourse.util.TextRefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class WordFilter {
    private final String WORDS_FOR_FILTER_EN = "the a an to and of in will he i is for his your they with not that him be them it who from on all my have was as me but are this their so then when had were what by has at or up we there if no her those into before our am these its she o about also through other after how which where would each any some why than off while until yet nor s ";
    private final String WORDS_FOR_FILTER_RU = "в нем тебе кого и в не что его на он я же но ибо с а как от к из в чтобы ему то вам они по вас если когда им о их все во кто за ты мы меня мне это у есть для так потому будет ли был или ни бы тогда который да нас него тебя тот было итак тебя нам них которые себя того были ее всех со вот до пред перед еще быть всем уже сего которого между только нему нет сие это себе дабы через там где ними при сам своих тем сей мой посему над ничего соего собою чем некоторые свою ко после своих ей под об те ";
    private final String WORDS_FOR_FILTER_UA = "і з що до його та як було її за про й були але р від для він їх лише між вони же це тому чи тим тільки своїх там над так навіть зокрема проте які при цього ж все всі всіх є свого т таким під після н ці серед цьому е своєї який ще із вона цих них зі також свої м уже цей коли через перед той отже може тобто якому того ні ними яка своїм щоб якого своє всього свій себе своїй якої якій або б вже без їхній їхнього їхні своїми де по ним ній тут йому буду яким тих то їм нього їхніх те цю мав собі ньому з-під цій деякі крім би всю ";
    private String wordsForFilter = new StringBuilder().append(WORDS_FOR_FILTER_EN).append(WORDS_FOR_FILTER_RU).append(WORDS_FOR_FILTER_UA).toString();

    public List<String> removeUnimportantWords(List<String> refinedWords) {

        TextRefiner textRefiner = new TextRefiner();
        List<String> filter = textRefiner.refineText(wordsForFilter);
        refinedWords.removeAll(filter);
        return refinedWords;
    }
}

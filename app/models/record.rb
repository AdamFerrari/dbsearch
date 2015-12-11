class Record < ActiveRecord::Base
  has_many :inverted_indices, dependent: :destroy
  validates :content, presence: true
  after_save :index_content

  protected
  def index_content
    words = {}
    self.content.downcase.split(/\W/).each do |word|
      if(word.size > 0)
        count = words[word] || 0
        count = count + 1
        words[word] = count
      end
    end

    ii_recs = []
    words.each_pair do |word, count|
      kw = Keyword.find_or_create_by(keyword: word)
      ii = InvertedIndex.new
      ii.record_id = self.id
      ii.keyword_id = kw.id
      ii.count = count
      ii_recs << ii
      #ii.save!
    end
    InvertedIndex.import ii_recs
  end

end

class AppController < ApplicationController
  def search
  end

  def results
    @search_terms = params[:search].downcase.split(/\W/).sort.uniq
    nil_term = false
    @keywords = @search_terms.collect do |term|
      keyword = Keyword.find_by(keyword: term)
      nil_term = true if keyword.nil?
      keyword.nil? ? nil : keyword
    end
    @results = Record.all
    if nil_term
      @results = @results.where('FALSE')
    else
      kwn = 0
      @keywords.each do |keyword|
        ii = "ii#{kwn}"
        @results = @results
          .joins("JOIN inverted_indices #{ii} ON records.id = #{ii}.record_id")
          .where("#{ii}.keyword_id = \'#{keyword.id}\'")
        kwn = kwn + 1
      end
    end
     @results = @results.paginate(:page => params[:page], :per_page => 10)
  end

  def new
  end

  def create
    @record = Record.new
    @record.content = params[:content]
    @record.save!
  end
end
